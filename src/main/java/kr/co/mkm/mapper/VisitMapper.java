package kr.co.mkm.mapper;

import kr.co.mkm.visit.service.SearchResult;
import kr.co.mkm.visit.service.SearchVisit;
import kr.co.mkm.visit.service.VisitVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitMapper {
    int insertVisitor(VisitVo vo);
    int isVisit(String ip);
    int getToday();
    int getYesterday();
    int getThisWeek();
    int getLastWeek();
    int getThisMonth();
    int getLastMonth();
    int getTotal();

    List<SearchResult> searchDay(SearchVisit searchVisit);
    List<SearchResult> searchWeek(SearchVisit searchVisit);
    List<SearchResult> searchMonth(SearchVisit searchVisit);
    List<SearchResult> searchYear(SearchVisit searchVisit);
}
