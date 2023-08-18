package kr.co.mkm.mapper;

import kr.co.mkm.client.web.service.CounselingVo;
import kr.co.mkm.client.web.service.EstimateVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface ClientMapper {
    void saveCounseling(CounselingVo vo);
    void saveEstimate(EstimateVo vo);
    List<CounselingVo> getCounselingList();
    CounselingVo getCounseling(String id);
    List<EstimateVo> getEstimateList();
    EstimateVo getEstimate(String id);
}
