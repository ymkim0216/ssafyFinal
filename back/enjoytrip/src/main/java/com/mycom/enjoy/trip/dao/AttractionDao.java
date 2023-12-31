package com.mycom.enjoy.trip.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.enjoy.trip.dto.AttractionDetailDto;
import com.mycom.enjoy.trip.dto.AttractionThumbnailDto;
import com.mycom.enjoy.trip.dto.CityDto;
import com.mycom.enjoy.trip.dto.RegionDto;
import com.mycom.enjoy.trip.dto.SearchParamDto;

@Mapper
public interface AttractionDao {
	List<CityDto> getCity();
	List<RegionDto> getRegion(int city);
	List<AttractionThumbnailDto> search(SearchParamDto searchParamDto);
	AttractionDetailDto searchDetail(int contentId);
	List<AttractionThumbnailDto> searchPopularAttr();
//	List<AttractionThumbnailDto> searchPopularAttrByAge(int stAge,int edAge);
	List<AttractionThumbnailDto> searchPopularAttrByAge();
	List<AttractionThumbnailDto> searchPopularAttrByDay();
	int attractionListTotalCount(SearchParamDto searchParamDto);
	//추가 
	List<AttractionThumbnailDto> searchRandom();
	List<AttractionThumbnailDto> searchMemberRandom(int memberRegion);
}
