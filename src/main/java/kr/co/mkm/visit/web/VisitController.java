package kr.co.mkm.visit.web;

import kr.co.mkm.visit.service.SearchResult;
import kr.co.mkm.visit.service.SearchVisit;
import kr.co.mkm.visit.service.VisitService;
import kr.co.mkm.visit.service.VisitStatics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class VisitController {
    @Autowired
    private VisitService visitService;

    @GetMapping("/api/visit")
    public String Main(HttpServletRequest request) {
        visitService.insertVisitor(request);
        return "";
    }

    @GetMapping("/api/admin/visit/statics")
    public VisitStatics getVisitStatics() {
        return visitService.getVisitStatics();
    }

    @PostMapping("/api/admin/visit/search")
    public List<SearchResult> getVisitSearch(@RequestBody SearchVisit searchVisit) {
        return visitService.getVisitSearch(searchVisit);
    }
}
