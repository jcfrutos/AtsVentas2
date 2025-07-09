package anexostransaccionales.atsventas.service;

public interface IEmailService {
    public void enviarCorreo(
      String para, String asunto, String contenido);

}