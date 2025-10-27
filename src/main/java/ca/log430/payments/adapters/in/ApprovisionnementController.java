package ca.log430.payments.adapters.in;

import ca.log430.payments.domain.Response;
import ca.log430.payments.domain.model.Approvisionnement;
import ca.log430.payments.domain.model.ApprovisionnementType;
import ca.log430.payments.ports.in.ApprovisionnementInterface;
import ca.log430.payments.ports.out.OrderRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/approvisionnements")
public class ApprovisionnementController implements ApprovisionnementInterface {

    OrderRepository orderRepository;
    Environment environment;
    public ApprovisionnementController(OrderRepository orderRepository, Environment environment) {
        this.orderRepository = orderRepository;
        this.environment = environment;
    }

    // request Order
    @PostMapping
    public ResponseEntity<Response<Approvisionnement>> createOrder(@RequestBody Approvisionnement ordre) {


        // persist ordre

        try {

            ordre.setStatus(ApprovisionnementType.SETTLED);

            // Call api to get current price
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            String token = this.environment.getProperty("token");
            headers.set("Authorization", "Bearer " + token); // <-- ton token JWT ici

            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Appeler le service distant avec les headers
            ResponseEntity<JsonNode> responseRest = restTemplate.exchange(
                    "http://" + this.environment.getProperty("GATEWAY_HOST") + "/users/" + ordre.getUserId(),
                    HttpMethod.GET,
                    entity,
                    JsonNode.class
            );

            JsonNode response = responseRest.getBody();

            int user_id = response.get("data").get("id").asInt();

            if (user_id != ordre.getUserId()) {
                return ResponseEntity.status(400).body(new Response<>(null, "User not found"));
            }

            ordre = this.orderRepository.save(ordre);
            return ResponseEntity.ok(new Response<>(ordre, null));


        } catch (Exception ex) {
            return ResponseEntity.status(500).body(new Response<>(null, ex.getMessage()));
        }
    }


    @GetMapping
    public ResponseEntity<Response<List<Approvisionnement>>> getAllOrders(@RequestParam(required = false) Integer userId) {
        try {

            if (userId != null) {
                List<Approvisionnement> userOrders = this.orderRepository.findOrdreByUserId(userId);
                return ResponseEntity.ok(new Response<>(userOrders, null));
            }
            List<Approvisionnement> orders = this.orderRepository.findAll();


            return ResponseEntity.ok(new Response<>(orders, null));
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(new Response<>(null, ex.getMessage()));
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Response<Approvisionnement>> getOrderById(@PathVariable Integer orderId) {
        try {
            Optional<Approvisionnement> ordre = this.orderRepository.findById(orderId);

            if (ordre.isEmpty()) {
                return ResponseEntity.status(404).body(new Response<>(null, "Order not found"));
            }

            return ResponseEntity.ok(new Response<>(ordre.get(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(new Response<>(null, ex.getMessage()));

        }
    }
}
