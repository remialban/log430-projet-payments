package ca.log430.payments.ports.in;

import ca.log430.payments.domain.Response;
import ca.log430.payments.domain.model.Approvisionnement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/approvisionnements")
public interface ApprovisionnementInterface  {
    @PostMapping
    public ResponseEntity<Response<Approvisionnement>> createOrder(@RequestBody Approvisionnement ordre);
    @GetMapping
    public ResponseEntity<Response<List<Approvisionnement>>> getAllOrders(@RequestParam(required = false) Integer userId);

    @GetMapping("/{orderId}")
    public ResponseEntity<Response<Approvisionnement>> getOrderById(@PathVariable Integer orderId);
}
