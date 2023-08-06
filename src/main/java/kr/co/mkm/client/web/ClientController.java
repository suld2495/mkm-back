package kr.co.mkm.client.web;

import kr.co.mkm.client.web.service.ClientService;
import kr.co.mkm.client.web.service.CounselingVo;
import kr.co.mkm.client.web.service.EstimateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping("/api/counseling")
    public boolean counseling(@RequestBody CounselingVo vo) {
        return clientService.saveCounseling(vo);
    }

    @PostMapping("/api/estimate")
    public boolean estimate(@RequestBody EstimateVo vo) {
        return clientService.saveEstimate(vo);
    }
}
