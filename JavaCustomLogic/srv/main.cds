using org.techyks as api from '../db/schema';

service BookshopService {
    entity Books as projection on api.Books;
}