package acm.javaspring;

import acm.javaspring.service.BibliotecaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Main {
    private BibliotecaService bibliotecaService = new BibliotecaService();
    private ObjectMapper mapper = new ObjectMapper().findAndRegisterModules().registerModule(new JavaTimeModule());
    public void printPrettyJson(Object obj)  {
        try{
            IO.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Main main = new Main();
        IO.println("Punto 1");

        main.printPrettyJson(main.bibliotecaService.mostRecentBookByAutor("Martin Fowler").get());
        IO.println("Punto 2");

        main.printPrettyJson(main.bibliotecaService.orderedBooksByYearFilterByRange(1990, 2010));
        IO.println("Punto 3");

        main.printPrettyJson(main.bibliotecaService.userStatistics());
        IO.println("Punto 4");

        main.printPrettyJson(main.bibliotecaService.overdueBooks());
        IO.println("Punto 5");

        main.printPrettyJson(main.bibliotecaService.enrichedGenreReport());
        IO.println("Punto 6");

        main.printPrettyJson(main.bibliotecaService.copyDisponibility());
        IO.println("Punto 7");

        main.printPrettyJson(main.bibliotecaService.autoresMasPrestados(4));
    }
}
