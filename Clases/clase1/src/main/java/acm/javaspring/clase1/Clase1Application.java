package acm.javaspring.clase1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootApplication
public class Clase1Application {

    private int multiplicador = 2;
    void main(String[] args) {
        SpringApplication.run(Clase1Application.class, args);
    }


    record Product(Double price, String name){
    }

    static class StringComparators implements Comparator<String>{
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }
    @Bean
    CommandLineRunner runner(){
        return args -> {
            // API STREAMS
            List<String> lista = List.of("a","b","c");
            Stream<String> s1 = lista.stream();
            Stream<String> s2 = lista.parallelStream();

            IntStream rango = IntStream.range(0,10);
            for(int i : rango.toArray()){
                System.out.println(i);
            }
            List<String> nombres = List.of("Juan","Ana","Pedro","Maria","Jorge", "Jota mario");
            Stream<String> filtrarNombres = nombres.stream().filter(s -> s.startsWith("J"));
            Stream<String> transformarAMayusculas = filtrarNombres.map(String::toUpperCase);
            List<String> resultado = transformarAMayusculas.toList();
                resultado.forEach(System.out::println);
            BiFunction<Integer,Integer, Double> division = (a,b) -> b != 0 ? (double) a / b : null;
            System.out.println(division.apply(10,0));
            var resultado2 = nombres.parallelStream()
                    .filter(s -> s.startsWith("J"))
                    .map(String::toUpperCase)
                    .collect(Collectors.toMap(s -> s, String::length));
            IO.println(resultado2);
        };
    }

//    // Recibir una funcion como parametro
//    public static <T,R> List<R> tranformar(List<T> lista, Function<T,R> f){
//        return lista.stream().map(f).toList();
//    }

}
