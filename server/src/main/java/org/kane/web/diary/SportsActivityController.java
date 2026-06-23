package org.kane.web.diary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.domain.DTO.entityDTO.diary.sport_activity.SportActivityEditDTO;
import org.kane.domain.DTO.entityDTO.diary.sport_activity.SportActivityShowDTO;
import org.kane.domain.DTO.entityDTO.diary.sport_activity.SportsActivityCreateDTO;
import org.kane.domain.DTO.response.MessageResponse;
import org.kane.domain.service.diary.sports_activity.SportsActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/sportsActivity")
@RequiredArgsConstructor
public class SportsActivityController {

    private final SportsActivityService sportsActivityService;

    @PutMapping("/create")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SportActivityShowDTO> createSportActivity(Principal principal, @RequestBody SportsActivityCreateDTO sportsActivityCreateDTO){
        var result = sportsActivityService.createActivity(principal, sportsActivityCreateDTO);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/edit")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SportActivityShowDTO> editActivity(@RequestBody SportActivityEditDTO sportActivityEditDTO){
        var result = sportsActivityService.updateSportActivity(sportActivityEditDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MessageResponse> deleteActivity(@PathVariable Long id){
        sportsActivityService.deleteSportActivity(id);
        return ResponseEntity.ok(new MessageResponse("Deleted"));
    }
}
