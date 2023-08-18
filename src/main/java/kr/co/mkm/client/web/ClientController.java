package kr.co.mkm.client.web;

import kr.co.mkm.client.web.service.ClientService;
import kr.co.mkm.client.web.service.CounselingVo;
import kr.co.mkm.client.web.service.EstimateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/api/admin/list/counseling")
    public List<CounselingVo> getCounselingList() {
        try {
            List<CounselingVo> map = clientService.getCounselingList();
            return map;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @GetMapping("/api/admin/counseling/{id}")
    public CounselingVo getCounseling(@PathVariable String id) {
        try {
            return clientService.getCounseling(id);
        } catch (Exception e) {
            return new CounselingVo();
        }
    }

    @GetMapping("/api/admin/list/estimate")
    public List<EstimateVo> getEstimateList() {
        try {
            return clientService.getEstimateList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @GetMapping("/api/admin/estimate/{id}")
    public EstimateVo getEstimate(@PathVariable String id) {
        try {
            return clientService.getEstimate(id);
        } catch (Exception e) {
            return new EstimateVo();
        }
    }
}
