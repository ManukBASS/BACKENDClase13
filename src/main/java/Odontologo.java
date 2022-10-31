import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Odontologo {

    private String apellido;
    private String nombre;
    private int matricula;

    public Odontologo(int matricula, String nombre, String apellido) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.matricula = matricula;
    }


}
