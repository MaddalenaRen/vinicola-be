package it.epicode.vinicola_be.repository;

import it.epicode.vinicola_be.model.FaseProduzione;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaseProduzioneRepository extends JpaRepository<FaseProduzione,Long> {
    Page<FaseProduzione> findByOperatore_Id(Long idOperatore, Pageable pageable);
    Page<FaseProduzione> findByLottoVino_Id(Long idOperatore,Pageable pageable);
}
