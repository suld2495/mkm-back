package kr.co.mkm.client.web.service;

import java.util.List;
import java.util.Map;

public interface ClientService {
    boolean saveCounseling(CounselingVo vo);

    boolean saveEstimate(EstimateVo vo);

    List<CounselingVo> getCounselingList() throws Exception;

    CounselingVo getCounseling(String id) throws Exception;

    List<EstimateVo> getEstimateList() throws Exception;

    EstimateVo getEstimate(String id) throws Exception;
}
