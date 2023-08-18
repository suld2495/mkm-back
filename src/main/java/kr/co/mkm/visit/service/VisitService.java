package kr.co.mkm.visit.service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface VisitService {
    int insertVisitor(HttpServletRequest request);
    VisitStatics getVisitStatics();

    List<SearchResult> getVisitSearch(SearchVisit searchVisit);
}
