Table overview;

String information="CREATE TABLE IF NOT EXISTS information(id SERIAL PRIMARY KEY,"
                           +"role VARCHAR(20) NOT NULL)";
                           
                           
                           
String login="CREATE TABLE IF NOT EXISTS login(" +"id SERIAL PRIMARY KEY,"
                                +"user_id INTEGER NOT NULL,"+"password INTEGER NOT NULL,"+"information_id INTEGER NOT NULL,"
                                +"CONSTRAINT fk_information FOREIGN KEY(information_id) REFERENCES information(id)on delete cascade)";
                                
                                
                                
String admin="CREATE TABLE IF NOT EXISTS admin_list(" +"id SERIAL PRIMARY KEY,"
                               +"admin_name VARCHAR(30) NOT NULL,"+"admin_mobileNo BIGINT NOT NULL,"
                               +"admin_status BOOLEAN DEFAULT 'true',"+"login_id INTEGER NOT NULL,"
                               +"CONSTRAINT fk_loginid FOREIGN KEY(login_id) REFERENCES login(id) on delete cascade)";
                               
                               
                               
String vendor="CREATE TABLE IF NOT EXISTS vendor_list(" +"id SERIAL PRIMARY KEY,"
                               +"vendor_name VARCHAR(30) NOT NULL,"+"vendor_mobileNo BIGINT NOT NULL,"
                               +"vendor_status BOOLEAN DEFAULT 'true',"+"vendor_location VARCHAR(50) NOT NULL)";
                               
                               
                               
String customer="CREATE TABLE IF NOT EXISTS customer_list(" +"id SERIAL PRIMARY KEY,"
                                +"customer_name VARCHAR(30) NOT NULL,"+"customer_mobileNo BIGINT NOT NULL,"
                                +"customer_location VARCHAR(50),"+"login_id INTEGER NOT NULL,"                        
                                +"customer_status BOOLEAN DEFAULT 'true',"+"CONSTRAINT fk_login FOREIGN KEY(login_id) REFERENCES login(id)on delete cascade)";                       
                                
                                
                                
String product="CREATE TABLE IF NOT EXISTS product_list(" +"id SERIAL PRIMARY KEY,"
                               +"product_name VARCHAR(40) NOT NULL,"+"brand_name VARCHAR(40) NOT NULL,"
                               +"product_status BOOLEAN DEFAULT 'true')";
                               
                               
                               
String purchase="CREATE TABLE IF NOT EXISTS purchase_details(" +"id SERIAL PRIMARY KEY,"
                               +"date timestamp NOT NULL,"+"vendor_id INTEGER NOT NULL,"
                               +"product_id INTEGER NOT NULL,"+"count INTEGER NOT NULL,"
                               +"CONSTRAINT fk_product_id FOREIGN KEY(product_id) REFERENCES product_list(id),"
                               +"CONSTRAINT fk_vendor_id FOREIGN KEY(vendor_id) REFERENCES vendor_list(id)on delete cascade)";
                               
                               
                               
String sale="CREATE TABLE IF NOT EXISTS sale_details("+ "id SERIAL PRIMARY KEY,"
                                   +"date timestamp NOT NULL,"+"customer_id INTEGER NOT NULL,"
                                   +"product_id INTEGER NOT NULL,"+"product_count INTEGER NOT NULL,"
                                   +"CONSTRAINT fk_customer_id FOREIGN KEY(customer_id) REFERENCES customer_list(id),"
                                   +"CONSTRAINT fk_product_id FOREIGN KEY(product_id) REFERENCES product_list(id)on delete cascade)";
                                                                                                   
String returntable="CREATE TABLE IF NOT EXISTS return_details("+ "id SERIAL PRIMARY KEY,"
                                   +"date timestamp NOT NULL,"+"customer_id INTEGER NOT NULL,"
                                   +"product_id INTEGER NOT NULL,"+"product_count INTEGER NOT NULL,"
                                   +"CONSTRAINT fk_customer_id FOREIGN KEY(customer_id) REFERENCES customer_list(id),"
                                   +"CONSTRAINT fk_product_id FOREIGN KEY(product_id) REFERENCES product_list(id)on delete cascade)";                                   
                                   
String stock="CREATE TABLE IF NOT EXISTS stock("+ "id SERIAL PRIMARY KEY,"
                             +"product_id INTEGER NOT NULL,"+"total_count INTEGER NOT NULL,"
                             +"CONSTRAINT fk_product_id FOREIGN KEY(product_id) REFERENCES product_list(id)on delete cascade)";                                                                  
                               
                               
                               
                                                                                                                                                                
