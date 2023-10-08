//package cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.service.exception;
//
//import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.service.Message;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//
//import java.time.LocalDateTime;
//
//@RestControllerAdvice
//public class ControllerExceptionHandler {
//
//    @ExceptionHandler(PlayerDuplicatedException.class)
//    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
//    public ResponseEntity<Message> playerDuplicatedExceptionHandler(PlayerDuplicatedException e, WebRequest request) {
//        return new ResponseEntity<>(new Message(HttpStatus.NOT_ACCEPTABLE.value(), LocalDateTime.now(), e.getMessage(), request.getDescription(false)), HttpStatus.NOT_ACCEPTABLE);
//    }
//
//    @ExceptionHandler(PlayerNotFoundException.class)
//    @ResponseStatus(value = HttpStatus.NOT_FOUND)
//    public ResponseEntity<Message> playerNotFoundExceptionHandler(PlayerNotFoundException e, WebRequest request) {
//        return new ResponseEntity<>(new Message(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), e.getMessage(), request.getDescription(false)), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(GamesByPlayerNotFoundException.class)
//    @ResponseStatus(value = HttpStatus.NOT_FOUND)
//    public ResponseEntity<Message> gamesByPlayerNotFoundExceptionHandler(GamesByPlayerNotFoundException e, WebRequest request) {
//        return new ResponseEntity<>(new Message(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), e.getMessage(), request.getDescription(false)), HttpStatus.NOT_FOUND);
//    }
//}
