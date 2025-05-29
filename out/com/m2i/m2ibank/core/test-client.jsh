
--module-path out
--add-modules com.m2i.m2ibank.core

import com.m2i.m2ibank.core.Client;

Client c = new Client("C001", "Alice");
System.out.println(c);
