package customer.javacustomlogic.handlers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.sap.cds.services.cds.CdsReadEventContext;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;

import cds.gen.bookshopservice.BookshopService_;

@Component
// @ServiceName("BookshopService")
@ServiceName(BookshopService_.CDS_NAME)
public class BookshopServiceHandler implements EventHandler {

    @On(event = "READ", entity = "BookshopService.Books")
    public void onReadBooks(CdsReadEventContext context) {
        System.out.println("This will be called on READ operation on Book Entity");
        Map<String, Object> book = new HashMap<>();
        book.put("ID", UUID.randomUUID().toString());
        book.put("title", "Hello Java");
        book.put("stock", 50);
        book.put("price", 500);

        List<Map<String, Object>> result = Collections.singletonList(book);

        // Override default READ behaviour
        context.setResult(result);

    }

}
