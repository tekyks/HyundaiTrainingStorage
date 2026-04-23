package customer.javacustomlogic.handlers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.cds.Result;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.Update;
import com.sap.cds.ql.cqn.CqnAnalyzer;
import com.sap.cds.reflect.CdsModel;
import com.sap.cds.services.cds.CdsReadEventContext;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.After;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.bookshopservice.BookshopService_;
import cds.gen.bookshopservice.Books;
import cds.gen.bookshopservice.Books_;
import cds.gen.bookshopservice.GetMeSomethingContext;
import cds.gen.bookshopservice.TotalStockContext;
import cds.gen.bookshopservice.BooksStockValueContext;
import cds.gen.bookshopservice.BooksSetPriceContext;
import cds.gen.bookshopservice.SubmitBookOrderContext;
import static com.sap.cds.ql.CQL.*;



@Component
// @ServiceName("BookshopService")
@ServiceName(BookshopService_.CDS_NAME)
public class BookshopServiceHandler implements EventHandler {

    //Life cycle event --> On
    //@On(event = "READ", entity = "BookshopService.Books")
    // public void onReadBooks(CdsReadEventContext context) {
    //     System.out.println("This will be called on READ operation on Book Entity");
    //     Map<String, Object> book = new HashMap<>();
    //     book.put("ID", UUID.randomUUID().toString());
    //     book.put("title", "Hello Java");
    //     book.put("stock", 50);
    //     book.put("price", 500);

    //     List<Map<String, Object>> result = Collections.singletonList(book);

    //     // Override default READ behaviour
    //     context.setResult(result);
    //     context.setCompleted();
    // }

    // //Life cycle event --> After
    // @After(event = "READ", entity = Books_.CDS_NAME)
    // public void afterReadBooks(CdsReadEventContext context){
    //     //lets collect the data
    //     Result result = context.getResult();

    //     //Convert Result --> List<Books>
    //     List<Books> books = result.listOf(Books.class);

    //     //lets build logic
    //     for (Books book : books){
    //         if(book.getTitle() != null){
    //             book.setTitle(book.getTitle() + "!!!!!!!!!!!!!!!");
    //         }
    //     }
    // }

    //Life cycle event --> After 
    //lets add 1 more field Category based on book title logic
    @After(event = "READ", entity = Books_.CDS_NAME)
    public void afterReadBooks(CdsReadEventContext context){
        //lets collect the data
        Result result = context.getResult();

        //Convert Result --> List<Books>
        List<Books> books = result.listOf(Books.class);

        //lets build logic
        for (Books book : books){
            if(book.getTitle() != null){
                //whenever book title is having Work its a Business Book otherwise its a normal Book
                //lets get the title first
                String title = book.getTitle().toLowerCase();
                if(title.contains("work")){
                    //add category as Buisness Book
                    book.put("category","Business Book");
                }else{
                    //add category as Normal Book
                    book.put("category","Normal Book");
                }
            }
        }
    }


    //Unbound Function Implementatuin
    @On(event = GetMeSomethingContext.CDS_NAME)
    public void getMeSomething(GetMeSomethingContext context){
        //set some hard coded data
        context.setResult("My Unbound Function Implementation !!!");
        context.setCompleted();
    }

    @Autowired
    private PersistenceService db;

    //Unbound Function Implementatuin
    @On(event = TotalStockContext.CDS_NAME)
    public void totalStock(TotalStockContext context){
        Result result = db.run(
            Select.from("BookshopService.Books").columns(sum(get("stock")).as("TOTAL")));
        
        int total = result.first().map(row -> {
            Object val = row.get("TOTAL");
            return (val instanceof Number) ? ((Number) val).intValue() : 0;
        }).orElse(0);

        context.setResult(total);
        context.setCompleted();
    }


    @Autowired
    CdsModel cdsModel;
    //Bound Function Implementation
    @On(event = BooksStockValueContext.CDS_NAME, entity = Books_.CDS_NAME)
    public void stockValue(BooksStockValueContext context) {
        // get the column stock and price from Books entity where ID is passed id

        CqnAnalyzer analyzer = CqnAnalyzer.create(cdsModel);
        Map<String, Object> keys = analyzer.analyze(context.getCqn()).targetKeys();

        System.out.println(context.getCqn());

        String id = keys.get("ID").toString();

        Result result = db.run(
                Select.from("BookshopService.Books").columns("stock", "price").where(b -> b.get("ID").eq(id)));

        int stockValue = result.first().map(row -> {
            Object stock = row.get("stock");
            Object price = row.get("price");
            int s = (stock instanceof Number) ? ((Number)stock).intValue() : 0;
            int p = (price instanceof Number) ? ((Number)price).intValue() : 0;
            return s * p;
        }).orElse(0);

        context.setResult(stockValue);
        context.setCompleted();

    }


    //Bound Action Implementation
    @On(event = BooksSetPriceContext.CDS_NAME, entity = Books_.CDS_NAME)
    public void setPrice(BooksSetPriceContext context) {
        // Extarct the id
        CqnAnalyzer analyzer = CqnAnalyzer.create(cdsModel);
        Map<String, Object> keys = analyzer.analyze(context.getCqn()).targetKeys();
        String id = keys.get("ID").toString();

        //Get the price param from action input
        Integer price = context.getPrice();
        //Update Book Price
        db.run(
            Update.entity(Books_.CDS_NAME).data("price", price).where(b -> b.get("ID").eq(id)));


        //Select and return updated book
        Result result = db.run(
                Select.from("BookshopService.Books").where(b -> b.get("ID").eq(id)));

        Books updateBook = result.first(Books.class).orElseThrow(() -> new RuntimeException("Books not found"));

        context.setResult(updateBook);
        context.setCompleted();

    }


    //Unbound Action Implementation
    @On(event = SubmitBookOrderContext.CDS_NAME)
    public void submitBookOrder(SubmitBookOrderContext context) {
        
        //Get the id and stock param from action input
        Integer orderedStock = context.getStock();
        String id = context.getId().toString();
        //get the current stock result
        Result result = db.run(
                Select.from("BookshopService.Books").columns("stock").where(b -> b.get("ID").eq(id)));

        int currentStock = result.first().map(row -> {
            Object val = row.get("stock");
            return (val instanceof Number) ? ((Number) val).intValue() : 0;
        }).orElseThrow(() -> new RuntimeException("Books not found"));

        //calculated updated stock
        int updatedStock = currentStock - orderedStock;

        //Update Book Stock
        db.run(
            Update.entity(Books_.CDS_NAME).data("stock", updatedStock).where(b -> b.get("ID").eq(id)));

        context.setResult(updatedStock);
        context.setCompleted();
    }

}
