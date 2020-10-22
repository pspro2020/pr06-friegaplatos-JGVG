package friegaplatos;

import java.text.DateFormat;
import java.time.LocalTime;
import java.util.ArrayList;

public class Bandeja {
    ArrayList<Plato> bandeja_platos = new ArrayList();
    int capacidad_bandeja = 10;

    public Bandeja( int num_platos){
        for(int i = 0; i<num_platos; i++){
            bandeja_platos.add(new Plato(i+1));
        }
    }

    public Plato sacar(String nombre) throws InterruptedException {
        Plato plato;

        synchronized (this){
            while(bandeja_platos.isEmpty()){
                System.out.println(nombre+" espera a que haya platos -> "+ LocalTime.now().getHour()+":"+LocalTime.now().getMinute()+":"+LocalTime.now().getSecond());
                wait();
            }
            plato = bandeja_platos.remove(0);
            System.out.println(nombre+" ha cogido el "+plato.toString()+" -> "+ LocalTime.now().getHour()+":"+LocalTime.now().getMinute()+":"+LocalTime.now().getSecond());
            notifyAll();
            return plato;
        }
    }

    public void colocar(Plato plato, String nombre) throws InterruptedException {
        synchronized (this){
            while(bandeja_platos.size() > capacidad_bandeja){
                System.out.println(nombre+" no puede colocar más platos-> "+ LocalTime.now().getHour()+":"+LocalTime.now().getMinute()+":"+LocalTime.now().getSecond());
                wait();
            }
            bandeja_platos.add(plato);
            System.out.println(nombre + " ha colocado el " +plato.toString()+" en bandeja -> "+ LocalTime.now().getHour()+":"+LocalTime.now().getMinute()+":"+LocalTime.now().getSecond());
            notifyAll();
        }
    }
}
