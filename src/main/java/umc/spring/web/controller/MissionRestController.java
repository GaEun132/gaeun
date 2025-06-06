package umc.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.MissionConverter;
import umc.spring.converter.StoreConverter;
import umc.spring.converter.UserConverter;
import umc.spring.domain.Mission;
import umc.spring.domain.Review;
import umc.spring.domain.User;
import umc.spring.domain.mapping.UserMission;
import umc.spring.service.missionService.MissionCommandService;
import umc.spring.service.userService.UserCommandService;
import umc.spring.validation.annotation.ExistStores;
import umc.spring.validation.annotation.ExistUsers;
import umc.spring.validation.annotation.GreaterThanZero;
import umc.spring.web.dto.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/missions")
public class MissionRestController {

    private final MissionCommandService missionCommandService;

    @PostMapping("/")
    public ApiResponse<MissionResponseDTO.JoinResultDTO> join(@RequestBody @Valid  MissionRequestDTO.JoinDTO request) {
        Mission mission = missionCommandService.joinMission(request);
        return ApiResponse.onSuccess(MissionConverter.toJoinResultDTO(mission));

    }

    @GetMapping("/{storeId}/missions")
    @Operation(summary = "특정 가게의 미션 목록 조회 API",description = "특정 가게의 미션들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "storeId", description = "가게의 아이디, path variable 입니다!")
    })
    public ApiResponse<MissionResponseDTO.MissionPreMissionListDTO> getMissionList(@ExistStores @PathVariable(name = "storeId") Long storeId, @GreaterThanZero @RequestParam(name = "page") Integer page){
        Page<Mission> missionList = missionCommandService.getMissionList(storeId, page);
        return ApiResponse.onSuccess(MissionConverter.missionPreMissionListDTO(missionList));
    }



}
