package kr.co.mkm.client.web.service.impl;

import kr.co.mkm.client.web.service.ClientService;
import kr.co.mkm.client.web.service.CounselingVo;
import kr.co.mkm.client.web.service.EstimateVo;
import kr.co.mkm.mapper.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientMapper clientMapper;

    private String emailfrom = "rh.kim@deful.co.kr";
    private String company = "엠케이엠 풀필먼트";
    private String color = "01adfe";

    @Autowired
    private JavaMailSender mailSender;

    private String getInfo(String[] info) {
        String str = "";

        for (int i = 0; i < info.length; i += 1) {
            str += info[i];

            if (i != info.length - 1) {
                str += ",";
            }
        }

        return str;
    }

    @Override
    public boolean saveCounseling(CounselingVo vo) {
        vo.setProductStr(getInfo(vo.getProduct()));

        if (vo.isAgree()) {
            vo.setAgreeYN("Y");
        } else {
            vo.setAgreeYN("N");
        }

        Map<String, Object> map = new HashMap<>();
        String content = "";
        content += "<div>";
        content += "    <h2 style='font-size: 20px;padding-bottom: 20px;margin-bottom: 20px;border-bottom: 2px solid #" + color + ";'>";
        content += "      <span style='color: #" + color + ";'>엠케이엠 풀필먼트</span>";
        content += "      <span>간편 상담</span>";
        content += "    </h2>";
        content += "    <div style='background: #f4f4f4;padding: 15px;'>";
        content += "      <ul style='padding: 0;padding-left: 20px;font-size: 14px;color: #222;'>";
        content += "        <li style='margin-bottom: 10px;'>";
        content += "          <span style='font-weight: bold;'>회사명</span>";
        content += "          <span>" + vo.getCompany() + "</span>";
        content += "        </li>";
        content += "        <li style='margin-bottom: 10px;'>";
        content += "          <span style='font-weight: bold;'>담당자명</span>";
        content += "          <span>" + vo.getName() + "</span>";
        content += "        </li>";
        content += "        <li style='margin-bottom: 10px;'>";
        content += "          <span style='font-weight: bold;'>연락처</span>";
        content += "          <span>" + vo.getPhone() + "</span>";
        content += "        </li>";
        content += "        <li style='margin-bottom: 10px;'>";
        content += "          <span style='font-weight: bold;'>이메일</span>";
        content += "          <span>" + vo.getEmail() + "</span>";
        content += "        </li>";
        content += "        <li style='margin-bottom: 10px;'>";
        content += "          <span style='font-weight: bold;'>상품정보</span>";
        content += "          <span>" + vo.getProductStr() + "</span>";
        content += "        </li>";
        content += "        <li>";
        content += "          <span style='font-weight: bold;'>상담내용</span>";
        content += "          <span>" + vo.getContent() + "</span>";
        content += "        </li>";
        content += "      </ul>";
        content += "    </div>";
        content += "  </div>";
        map.put("contents", content);
        map.put("subject", "[" + company + "] 간편 상담");
        boolean check = emailSend(map);

        clientMapper.saveCounseling(vo);
        return check;
    }

    @Override
    public boolean saveEstimate(EstimateVo vo) {
        vo.setProductStr(getInfo(vo.getProduct()));
        vo.setStorageStr(getInfo(vo.getStorage()));

        if (vo.isAgree()) {
            vo.setAgreeYN("Y");
        } else {
            vo.setAgreeYN("N");
        }

        Map<String, Object> map = new HashMap<>();
        String content = "";

        content += "<div>";
        content += "  <h2 style='font-size: 20px;padding-bottom: 20px;margin-bottom: 20px;border-bottom: 2px solid #" + color + ";'>";
        content += "    <span style='color: #" + color + ";'>엠케이엠 풀필먼트</span>";
        content += "    <span>물류 견적 상담</span>";
        content += "  </h2>";
        content += "  <div style='background: #f4f4f4;padding: 15px;''>";
        content += "    <ul style='padding: 0;padding-left: 20px;font-size: 14px;color: #222;'>";
        content += "      <li>";
        content += "        <span style='font-weight: bold;'>상품분류</span>";
        content += "        <span>" + vo.getProducttype() + "</span>";
        content += "      </li>";
        content += "      <li>";
        content += "        <span style='font-weight: bold;'>상품정보</span>";
        content += "        <span>" + getInfo(vo.getProduct()) + "</span>";
        content += "      </li>";
        content += "      <li>";
        content += "        <span style='font-weight: bold;'>보관형태</span>";
        content += "        <span>" + getInfo(vo.getStorage()) + "</span>";
        content += "      </li>";
        content += "    </ul>";
        content += "";
        content += "    <h4 style='color: #" + color + ";'>월 보관 및 입/출고량</h4>";
        content += "    <ul style='padding: 0;padding-left: 20px;font-size: 14px;color: #222;'>";
        content += "      <li>";
        content += "        <span style='font-weight: bold;'>월 보관량</span>";
        content += "        <span>(상품수량 " + vo.getCount() + "개 / 파레트랙 " + vo.getPallettrack() + "개 / 경량랙 " + vo.getLightweightrack() + "개 / 박스 " + vo.getBox() + "개)</span>";
        content += "      </li>";
        content += "      <li>";
        content += "        <span style='font-weight: bold;'>월 입고량</span>";
        content += "        <span>" + vo.getImport1() + "개</span>";
        content += "      </li>";
        content += "      <li>";
        content += "        <span style='font-weight: bold;'>월 출고량</span>";
        content += "        <span>" + vo.getExport() + "개</span>";
        content += "      </li>";
        content += "      <li>";
        content += "        <span style='font-weight: bold;'>월 택배건수</span>";
        content += "        <span>" + vo.getDelivery() + "개</span>";
        content += "      </li>";
        content += "      <li>";
        content += "        <span style='font-weight: bold;'>SKU 상품수</span>";
        content += "        <span>" + vo.getSku() + "개</span>";
        content += "      </li>";
        content += "      <li>";
        content += "        <span style='font-weight: bold;'>택배포장방식</span>";
        content += "        <span>" + vo.getDeliveryType() + "</span>";
        content += "      </li>";
        content += "      <li>";
        content += "        <span style='font-weight: bold;'>임가공작업</span>";
        content += "        <span>" + vo.getProcessing() + "</span>";
        content += "      </li>";
        content += "      <li>";
        content += "        <span style='font-weight: bold;'>에어캡사용</span>";
        content += "        <span>" + vo.getAircap() + "</span>";
        content += "      </li>";
        content += "      <li>";
        content += "        <span style='font-weight: bold;'>반품엽서등</span>";
        content += "        <span>" + vo.getReturn1() + "</span>";
        content += "      </li>";
        content += "      <li>";
        content += "        <span style='font-weight: bold;'>상담내용</span>";
        content += "        <span>" + vo.getContent() + "</span>";
        content += "      </li>";
        content += "    </ul>";
        content += "";
        content += "    <h4 style='color: #" + color + ";'>기본정보 입력</h4>";
        content += "    <ul style='padding: 0;padding-left: 20px;font-size: 14px;color: #222;'>";
        content += "      <li>";
        content += "        <span style='font-weight: bold;'>회사명</span>";
        content += "        <span>" + vo.getCompany() + "</span>";
        content += "      </li>";
        content += "      <li>";
        content += "        <span style='font-weight: bold;'>담당자명</span>";
        content += "        <span>" + vo.getName() + "</span>";
        content += "      </li>";
        content += "      <li>";
        content += "        <span style='font-weight: bold;'>연락처</span>";
        content += "        <span>" + vo.getPhone() + "</span>";
        content += "      </li>";
        content += "      <li>";
        content += "        <span style='font-weight: bold;'>이메일</span>";
        content += "        <span>" + vo.getEmail() + "</span>";
        content += "      </li>";
        content += "    </ul>";
        content += "  </div>";
        content += "</div>";

        map.put("contents", content);
        map.put("subject", "[" + company + "] 물류 견적 상담");
        boolean check = emailSend(map);

        clientMapper.saveEstimate(vo);
        return check;
    }

    public boolean emailSend(Map<String, Object> param) {
        String setfrom = this.emailfrom;
        String setto = this.emailfrom;
        String board_content = param.get("contents").toString();
        String subject = param.get("subject").toString();

        try {
            MimeMessage message = this.mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setFrom(setfrom);
            messageHelper.setTo(setto);
            messageHelper.setText(board_content, board_content);
            messageHelper.setSubject(subject);
            this.mailSender.send(message);
            return true;
        } catch (Exception var9) {
            return false;
        }
    }

    @Override
    public List<CounselingVo> getCounselingList() {
        return clientMapper.getCounselingList();
    }

    @Override
    public CounselingVo getCounseling(String id) throws Exception {
        return clientMapper.getCounseling(id);
    }

    @Override
    public List<EstimateVo> getEstimateList() {
        return clientMapper.getEstimateList();
    }

    @Override
    public EstimateVo getEstimate(String id) throws Exception {
        return clientMapper.getEstimate(id);
    }
}
