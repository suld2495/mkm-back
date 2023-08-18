package kr.co.mkm.visit.service.impl;

import kr.co.mkm.mapper.VisitMapper;
import kr.co.mkm.visit.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class VisitServiceImpl implements VisitService {
    @Autowired
    private VisitMapper visitMapper;

    private String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
    @Override
    public int insertVisitor(HttpServletRequest request) {
        VisitVo vo = new VisitVo();
        vo.setIp(getClientIpAddr(request));
        vo.setAgent(request.getHeader("User-Agent"));//브라우저 정보
        vo.setRefer(request.getHeader("referer"));//접속 전 사이트 정보

        int result = visitMapper.isVisit(vo.getIp());

        if (result == 1
            ||"127.0.0.1".equals(vo.getIp())
            || "localhost".equals(vo.getIp())) {
            return 1;
        }

        return visitMapper.insertVisitor(vo);
    }

    public VisitStatics getVisitStatics() {
        int today = visitMapper.getToday();
        int yesterday = visitMapper.getYesterday();
        int thisWeek = visitMapper.getThisWeek();
        int lastWeek = visitMapper.getLastWeek();
        int thisMonth = visitMapper.getThisMonth();
        int lastMonth = visitMapper.getLastMonth();
        int total = visitMapper.getTotal();

        VisitStatics visitStatics = new VisitStatics();
        visitStatics.setToday(today);
        visitStatics.setYesterday(yesterday);
        visitStatics.setThisWeek(thisWeek);
        visitStatics.setLastWeek(lastWeek);
        visitStatics.setThisMonth(thisMonth);
        visitStatics.setLastMonth(lastMonth);
        visitStatics.setTotal(total);
        visitStatics.setDiffToday(visitStatics.getToday() - visitStatics.getYesterday());
        visitStatics.setDiffWeek(visitStatics.getThisWeek() - visitStatics.getLastWeek());
        visitStatics.setDiffMonth(visitStatics.getThisMonth() - visitStatics.getLastMonth());

        return visitStatics;
    }

    @Override
    public List<SearchResult> getVisitSearch(SearchVisit searchVisit) {
        if (searchVisit.getType().equals("일간")) {
            return visitMapper.searchDay(searchVisit);
        }

        if (searchVisit.getType().equals("주간")) {
            return visitMapper.searchWeek(searchVisit);
        }

        if (searchVisit.getType().equals("월간")) {
            return visitMapper.searchMonth(searchVisit);
        }

        if (searchVisit.getType().equals("연간")) {
            return visitMapper.searchYear(searchVisit);
        }

        return new ArrayList<>();
    }
}
