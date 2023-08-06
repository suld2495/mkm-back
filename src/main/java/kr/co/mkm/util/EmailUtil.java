package kr.co.mkm.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
    @Autowired
    private JavaMailSender mailSender;
    private String emailfrom = "hmdesign6242@gmail.com";

    public EmailUtil() {
    }

    public boolean emailSend(Map<String, Object> param) {
        String setfrom = this.emailfrom;
        String setto = param.get("email").toString();
        String board_content = param.get("contents").toString();
        String subject = param.get("subject").toString();

        try {
            MimeMessage message = this.mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setFrom(setfrom);
            messageHelper.setTo(setto);
            messageHelper.setText(board_content, board_content);
            messageHelper.setSubject(subject);
            if (param.get("file") != null) {
                FileSystemResource fsr = new FileSystemResource(param.get("path").toString() + "/" + param.get("file").toString());
                messageHelper.addAttachment(param.get("file").toString(), fsr);
            }

            this.mailSender.send(message);
            return true;
        } catch (Exception var9) {
            return false;
        }
    }

    public boolean subscriptSend(Map<String, Object> map) {
        String content = "";
        content = content + "<table style='border-top: 2px solid #1a1a1a;border-bottom: 2px solid #808080;width:100%;max-width:1200px;margin:0 auto;border-collapse:collapse;'>";
        content = content + "<tbody>";
        content = content + "\t<tr align='left' style='border-bottom: 1px solid #dadada;'>";
        content = content + "\t\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>이름</th>";
        content = content + "\t\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("name") + "</td>";
        content = content + "\t</tr>";
        content = content + "\t<tr align='left'>";
        content = content + "\t\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>이메일</th>";
        content = content + "\t\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("email1") + "@" + map.get("email2") + "</td>";
        content = content + "\t</tr>";
        content = content + "\t<tr align='left'>";
        content = content + "\t\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>연락처</th>";
        content = content + "\t\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("phone") + "</td>";
        content = content + "\t</tr>";
        content = content + "\t<tr align='left'>";
        content = content + "\t\t<th style='padding:18px 0;padding-left:26px;width:202px;border-bottom: 1px solid #dadada;'>내용</th>";
        content = content + "\t\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("content") + "</td>";
        content = content + "\t</tr>";
        content = content + "</tbody>";
        content = content + "</table>";
        map.put("contents", content);
        return this.emailSend(map);
    }

    public void requestSend(Map<String, Object> map) {
        String content = "";
        content = content + "<table style='border-top: 2px solid #1a1a1a;border-bottom: 2px solid #808080;width:100%;max-width:1200px;margin:0 auto;border-collapse:collapse;'>";
        content = content + "<tbody>";
        content = content + "\t<tr align='left' style='border-bottom: 1px solid #dadada;'>";
        content = content + "\t\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>이름</th>";
        content = content + "\t\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("name") + "</td>";
        content = content + "\t</tr>";
        content = content + "\t<tr align='left'>";
        content = content + "\t\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>연락처</th>";
        content = content + "\t\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("phone") + "</td>";
        content = content + "\t</tr>";
        content = content + "\t<tr align='left'>";
        content = content + "\t\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>방문경로</th>";
        content = content + "\t\t<td style='border-bottom: 1px solid #dadada;'>";

        String building;
        Iterator var4;
        for(var4 = ((List)map.get("access")).iterator(); var4.hasNext(); content = content + building + "/") {
            building = (String)var4.next();
        }

        content = content.substring(0, content.length() - 1);
        if (map.get("accessEtcText") != null && !map.get("accessEtcText").equals("")) {
            content = content + "(" + map.get("accessEtcText").toString() + ")";
        }

        content = content + "</td>";
        content = content + "\t</tr>";
        content = content + "\t<tr align='left'>";
        content = content + "\t\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>신축공사 주소</th>";
        content = content + "\t\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("address") + " " + map.get("addressDetail") + "</td>";
        content = content + "\t</tr>";
        content = content + "<tr align='left'>";
        content = content + "<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>건물종류</th>";
        content = content + "\t\t<td style='border-bottom: 1px solid #dadada;'>";

        for(var4 = ((List)map.get("building")).iterator(); var4.hasNext(); content = content + building + "/") {
            building = (String)var4.next();
        }

        content = content.substring(0, content.length() - 1);
        if (map.get("buildingEtcText") != null && !map.get("buildingEtcText").equals("")) {
            content = content + "(" + map.get("buildingEtcText").toString() + ")";
        }

        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>공사시작일</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("dateStart") + "</td>";
        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>입주예정일</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("moveStart") + "</td>";
        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>예산</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("burget") + "</td>";
        content = content + "</tr>";
        content = content + "</tbody>";
        content = content + "</table>";
        map.put("contents", content);
        this.emailSend(map);
    }

    public void intRequestSend(Map<String, Object> map) {
        String content = "";
        content = content + "<table style='border-top: 2px solid #1a1a1a;border-bottom: 2px solid #808080;width:100%;max-width:1200px;margin:0 auto;border-collapse:collapse;'>";
        content = content + "<tbody>";
        content = content + "\t<tr align='left' style='border-bottom: 1px solid #dadada;'>";
        content = content + "\t\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>이름</th>";
        content = content + "\t\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("name") + "</td>";
        content = content + "\t</tr>";
        content = content + "\t<tr align='left'>";
        content = content + "\t\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>연락처</th>";
        content = content + "\t\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("phone") + "</td>";
        content = content + "\t</tr>";
        content = content + "\t<tr align='left'>";
        content = content + "\t\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>방문경로</th>";
        content = content + "\t\t<td style='border-bottom: 1px solid #dadada;'>";

        String bottom;
        Iterator var4;
        for(var4 = ((List)map.get("access")).iterator(); var4.hasNext(); content = content + bottom + "/") {
            bottom = (String)var4.next();
        }

        content = content.substring(0, content.length() - 1);
        if (map.get("accessEtcText") != null && !map.get("accessEtcText").equals("")) {
            content = content + "(" + map.get("accessEtcText").toString() + ")";
        }

        content = content + "\t</td>";
        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>건물평수(분양평수)</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("equality") + "</td>";
        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>건물종류</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>";

        for(var4 = ((List)map.get("building")).iterator(); var4.hasNext(); content = content + bottom + "/") {
            bottom = (String)var4.next();
        }

        content = content.substring(0, content.length() - 1);
        if (map.get("buildingEtcText") != null && !map.get("buildingEtcText").equals("")) {
            content = content + "(" + map.get("buildingEtcText").toString() + ")";
        }

        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>주소</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("address") + " " + map.get("addressDetail") + "</td>";
        content = content + "</tr>";
        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>공사시작일</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("dateStart") + "</td>";
        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>입주예정일</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("moveStart") + "</td>";
        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>예산</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("burget") + "</td>";
        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>공사구분</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>";

        for(var4 = ((List)map.get("corp")).iterator(); var4.hasNext(); content = content + bottom + "/") {
            bottom = (String)var4.next();
        }

        content = content.substring(0, content.length() - 1);
        if (map.get("corpEtcText") != null && !map.get("corpEtcText").equals("")) {
            content = content + "(" + map.get("corpEtcText").toString() + ")";
        }

        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>확장공사</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("expand") + "</td>";
        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>철거공사</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("destroy") + "</td>";
        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>전기공사</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>";

        for(var4 = ((List)map.get("light")).iterator(); var4.hasNext(); content = content + bottom + "/") {
            bottom = (String)var4.next();
        }

        content = content.substring(0, content.length() - 1);
        if (map.get("lightEtcText") != null && !map.get("lightEtcText").equals("")) {
            content = content + "(" + map.get("lightEtcText").toString() + ")";
        }

        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>창호공사</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("window") + "</td>";
        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>도배공사</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>";

        for(var4 = ((List)map.get("paper")).iterator(); var4.hasNext(); content = content + bottom + "/") {
            bottom = (String)var4.next();
        }

        content = content.substring(0, content.length() - 1);
        if (map.get("paperEtcText") != null && !map.get("paperEtcText").equals("")) {
            content = content + "(" + map.get("paperEtcText").toString() + ")";
        }

        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>바닥공사</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>";

        for(var4 = ((List)map.get("bottom")).iterator(); var4.hasNext(); content = content + bottom + "/") {
            bottom = (String)var4.next();
        }

        content = content.substring(0, content.length() - 1);
        if (map.get("bottomEtcText") != null && !map.get("bottomEtcText").equals("")) {
            content = content + "(" + map.get("bottomEtcText").toString() + ")";
        }

        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>욕실공사</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("bath") + "</td>";
        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>주방가구</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("kitchen") + "</td>";
        content = content + "</tr>";
        content = content + "</tbody>";
        content = content + "</table>";
        map.put("contents", content);
        this.emailSend(map);
    }

    public void poolRequestSend(Map<String, Object> map) {
        String content = "";
        content = content + "<table style='border-top: 2px solid #1a1a1a;border-bottom: 2px solid #808080;width:100%;max-width:1200px;margin:0 auto;border-collapse:collapse;'>";
        content = content + "<tbody>";
        content = content + "\t<tr align='left' style='border-bottom: 1px solid #dadada;'>";
        content = content + "\t\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>이름</th>";
        content = content + "\t\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("name") + "</td>";
        content = content + "\t</tr>";
        content = content + "\t<tr align='left'>";
        content = content + "\t\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>연락처</th>";
        content = content + "\t\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("phone") + "</td>";
        content = content + "\t</tr>";
        content = content + "\t<tr align='left'>";
        content = content + "\t\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>방문경로</th>";
        content = content + "\t\t<td style='border-bottom: 1px solid #dadada;'>";

        String building;
        Iterator var4;
        for(var4 = ((List)map.get("access")).iterator(); var4.hasNext(); content = content + building + "/") {
            building = (String)var4.next();
        }

        content = content.substring(0, content.length() - 1);
        if (map.get("accessEtcText") != null && !map.get("accessEtcText").equals("")) {
            content = content + "(" + map.get("accessEtcText").toString() + ")";
        }

        content = content + "</td>";
        content = content + "\t</tr>";
        content = content + "\t<tr align='left'>";
        content = content + "\t\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>현지 지역</th>";
        content = content + "\t\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("area") + "</td>";
        content = content + "\t</tr>";
        content = content + "\t<tr align='left'>";
        content = content + "<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>법인설립 & 건축시공<br> 및 임대 통합관리</th>";
        content = content + "\t\t<td style='border-bottom: 1px solid #dadada;'>";

        for(var4 = ((List)map.get("building")).iterator(); var4.hasNext(); content = content + building + "/") {
            building = (String)var4.next();
        }

        content = content.substring(0, content.length() - 1);
        if (map.get("buildingEtcText") != null && !map.get("buildingEtcText").equals("")) {
            content = content + "(" + map.get("buildingEtcText").toString() + ")";
        }

        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>예산</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("burget") + "</td>";
        content = content + "</tr>";
        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>프로젝트 시작일</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("projectStart") + "</td>";
        content = content + "</tr>";
        content = content + "</tbody>";
        content = content + "</table>";
        map.put("contents", content);
        this.emailSend(map);
    }

    public void selfRequestSend(Map<String, Object> map) {
        String content = "";
        content = content + "<table style='border-top: 2px solid #1a1a1a;border-bottom: 2px solid #808080;width:100%;max-width:1200px;margin:0 auto;border-collapse:collapse;'>";
        content = content + "<tbody>";
        content = content + "\t<tr align='left' style='border-bottom: 1px solid #dadada;'>";
        content = content + "\t\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>이름</th>";
        content = content + "\t\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("name") + "</td>";
        content = content + "\t</tr>";
        content = content + "\t<tr align='left'>";
        content = content + "\t\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>연락처</th>";
        content = content + "\t\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("phone") + "</td>";
        content = content + "\t</tr>";
        content = content + "\t<tr align='left'>";
        content = content + "\t\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>방문경로</th>";
        content = content + "\t\t<td style='border-bottom: 1px solid #dadada;'>";

        String corp;
        Iterator var4;
        for(var4 = ((List)map.get("access")).iterator(); var4.hasNext(); content = content + corp + "/") {
            corp = (String)var4.next();
        }

        content = content.substring(0, content.length() - 1);
        if (map.get("accessEtcText") != null && !map.get("accessEtcText").equals("")) {
            content = content + "(" + map.get("accessEtcText").toString() + ")";
        }

        content = content + "</td>";
        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>건물평수(분양평수)</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("equality") + "</td>";
        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>건물종류</th>";
        content = content + "\t\t<td style='border-bottom: 1px solid #dadada;'>";

        for(var4 = ((List)map.get("building")).iterator(); var4.hasNext(); content = content + corp + "/") {
            corp = (String)var4.next();
        }

        content = content.substring(0, content.length() - 1);
        if (map.get("buildingEtcText") != null && !map.get("buildingEtcText").equals("")) {
            content = content + "(" + map.get("buildingEtcText").toString() + ")";
        }

        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>주소</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("address") + " " + map.get("addressDetail") + "</td>";
        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>공사시작일</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("dateStart") + "</td>";
        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>입주예정일</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("moveStart") + "</td>";
        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "\t<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>예산</th>";
        content = content + "\t<td style='border-bottom: 1px solid #dadada;'>" + map.get("burget") + "</td>";
        content = content + "</tr>";
        content = content + "<tr align='left'>";
        content = content + "<th style='padding-left:26px;width:202px;height:57px;line-height:57px;border-bottom: 1px solid #dadada;'>공사구분</th>";
        content = content + "\t\t<td style='border-bottom: 1px solid #dadada;'>";

        for(var4 = ((List)map.get("corp")).iterator(); var4.hasNext(); content = content + corp + "/") {
            corp = (String)var4.next();
        }

        content = content.substring(0, content.length() - 1);
        if (map.get("corpEtcText") != null && !map.get("corpEtcText").equals("")) {
            content = content + "(" + map.get("corpEtcText").toString() + ")";
        }

        content = content + "</tr>";
        content = content + "</tbody>";
        content = content + "</table>";
        map.put("contents", content);
        this.emailSend(map);
    }
}
