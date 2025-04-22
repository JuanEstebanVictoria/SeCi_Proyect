package co.edu.uniquindio.seci_proyect.services.impl;

import co.edu.uniquindio.seci_proyect.Model.*;
import co.edu.uniquindio.seci_proyect.exceptions.*;
import co.edu.uniquindio.seci_proyect.repositories.ReportRepository;
import co.edu.uniquindio.seci_proyect.repositories.UserRepository;
import co.edu.uniquindio.seci_proyect.repositories.VoteRepository;
import co.edu.uniquindio.seci_proyect.services.interfaces.VoteService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private VoteRepository voteRepository;
    private ReportRepository reportRepository;
    private UserRepository userRepository;

    @Override
    @Transactional
    public void addVote(String reportId, String userId) {

        ObjectId userObjId = new ObjectId(userId);
        ObjectId reportObjId = new ObjectId(reportId);

        if(voteRepository.existsByUserIdAndReportId(userObjId, reportObjId)) {
            throw new BusinessRuleException("El usuario ya ha votado este reporte");
        }

        Vote vote = new Vote();
        vote.setUserId(userObjId);
        vote.setReportId(reportObjId);
        vote.setTimestamp(LocalDateTime.now());

        voteRepository.save(vote);
        // Verificar que el reporte existe y está activo
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado"));

        if(report.getStatus() == ReportStatus.DELETED) {
            throw new BusinessRuleException("No se puede votar un reporte eliminado");
        }

        // Verificar que el usuario existe y está activo
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        if(user.getStatus() != UserStatus.ACTIVE) {
            throw new AuthorizationException("Usuario no está activo");
        }
    }

    @Override
    @Transactional
    public void removeVote(String voteId) {
        if(!voteRepository.existsById(voteId)) {
            throw new ResourceNotFoundException("Voto no encontrado");
        }
        voteRepository.deleteById(voteId);
    }

    @Override
    public int countVotesByReport(String reportId) {
        return (int) voteRepository.countByReportId(new ObjectId(reportId));
    }

    @Override
    public boolean hasUserVoted(String reportId, String userId) {
        return voteRepository.existsByUserIdAndReportId(new ObjectId(userId), new ObjectId(reportId));
    }
}