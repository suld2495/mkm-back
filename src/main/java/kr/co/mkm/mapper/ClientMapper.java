package kr.co.mkm.mapper;

import kr.co.mkm.client.web.service.CounselingVo;
import kr.co.mkm.client.web.service.EstimateVo;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientMapper {
    void saveCounseling(CounselingVo vo);
    void saveEstimate(EstimateVo vo);
}
