package com.tms;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Children Page",
                description = "My favorite project!",
                contact = @Contact(
                        name = "Unuchko Mikhail",
                        email = "Wnuk1988@mail.ru"
                )

        )
)
@SpringBootApplication
public class ChildrenPageApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChildrenPageApplication.class, args);
    }
}
