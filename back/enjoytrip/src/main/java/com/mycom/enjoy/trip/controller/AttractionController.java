package com.mycom.enjoy.trip.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.enjoy.member.dto.MemberDto;
import com.mycom.enjoy.trip.dto.AttractionDetailDto;
import com.mycom.enjoy.trip.dto.AttractionReplyParamDto;
import com.mycom.enjoy.trip.dto.AttractionReplyResultDto;
import com.mycom.enjoy.trip.dto.AttractionThumbnailDto;
import com.mycom.enjoy.trip.dto.CityDto;
import com.mycom.enjoy.trip.dto.RegionDto;
import com.mycom.enjoy.trip.dto.SearchParamDto;
import com.mycom.enjoy.trip.dto.SearchResultDto;
import com.mycom.enjoy.trip.service.AttractionReplyService;
import com.mycom.enjoy.trip.service.AttractionService;

@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AttractionController {
	@Autowired
	AttractionService service;
	@Autowired
	AttractionReplyService replyService;

	@GetMapping(value = "/trip") // 도시 정보
	public List<CityDto> getCity() {
		List<CityDto> list = service.getCity();
		return list;
	}

	@GetMapping(value = "/trip/getRegion/{city}") // 지역 정보
	public List<RegionDto> getRegion(@PathVariable int city) {
		List<RegionDto> list = service.getRegion(city);
		return list;
	}

	@GetMapping(value = "/trip/search/") // 일반 검색 결과
	public SearchResultDto search(SearchParamDto searchParamDto) {
		return service.search(searchParamDto);
	}

	@GetMapping(value = "/trip/searchDetail/{contentId}") // 상세 결과
	public AttractionDetailDto searchDetail(@PathVariable int contentId) {
		AttractionDetailDto description = service.searchDetail(contentId);
		return description;
	}

	@GetMapping("/trip/searchPopular")
	public List<AttractionThumbnailDto> searchPopularAttr() {
		return service.searchPopularAttr();
	}

//	@GetMapping("/trip/searchPopularAge")
//	public List<AttractionThumbnailDto> searchPopularAttrByAge(HttpSession session) {
//		MemberDto dto = (MemberDto) session.getAttribute("memberDto");
//		int age = Integer.parseInt(dto.getMemberBirth().substring(0,4));
//		LocalDate now = LocalDate.now();
//        int stAge = ((now.getYear()-age)/10) * 10;
//		int edAge = stAge+9;
//		return service.searchPopularAttrByAge(stAge,edAge);
//	}
	@GetMapping("/trip/searchPopularAge") // 단순 연령별 인기 여행지 -> 로그인 하지 않았을 경우, 해당 칸이 공란이 되는 것을 방지
	public List<AttractionThumbnailDto> searchPopularAttrByAge() {
		return service.searchPopularAttrByAge();
	}

	@GetMapping("/trip/searchPopularDay")
	public List<AttractionThumbnailDto> searchPopularAttrByDay() {
		return service.searchPopularAttrByDay();
	}
	//추가 
	@GetMapping("/trip/searchRandom") 
	public List<AttractionThumbnailDto> searchRandom() {
		return service.searchRandom();
	}

	@GetMapping("/trip/searchMemberRandom/{region}")
	public List<AttractionThumbnailDto> searchMemberRandom(@PathVariable int region) {
		return service.searchMemberRandom(region);
	}
	
	
	
	// reply
	@GetMapping("/trip/replys")
	public AttractionReplyResultDto getReply(@ModelAttribute AttractionReplyParamDto paramDto) {
		return replyService.getReply(paramDto);
	}

	@PostMapping("/trip/replys")
	public AttractionReplyResultDto replyInsert(@RequestBody AttractionReplyParamDto replyParamDto,
			HttpSession session) {
		MemberDto member = (MemberDto) session.getAttribute("memberDto");
		if (member != null) {
			replyParamDto.setMemberId(member.getMemberId());
		} else {
			AttractionReplyResultDto resultDto = new AttractionReplyResultDto();
			resultDto.setResult(-1);
			return resultDto;
		}
		return replyService.replyInsert(replyParamDto);
	}

	@PutMapping("/trip/replys")
	public AttractionReplyResultDto replyUpdate(@RequestBody AttractionReplyParamDto replyParamDto,
			HttpSession session) {
		MemberDto member = (MemberDto) session.getAttribute("memberDto");

		if (member != null) {
			replyParamDto.setMemberId(member.getMemberId());
		} else {
			AttractionReplyResultDto resultDto = new AttractionReplyResultDto();
			resultDto.setResult(-1);
			return resultDto;
		}
		return replyService.replyUpdate(replyParamDto);
	}

	@DeleteMapping("/trip/replys/{replyId}")
	public AttractionReplyResultDto replyDelete(@PathVariable int replyId) {
		return replyService.replyDelete(replyId);
	}
}
