package com.soutenence.publiciteApp.service;

import com.soutenence.publiciteApp.Mapper.TypePanneauMapperClass;
import com.soutenence.publiciteApp.ResponseAndRequest.TypePanRequest;
import com.soutenence.publiciteApp.ResponseAndRequest.TypePanResponse;
import com.soutenence.publiciteApp.entity.TypePanneau;
import com.soutenence.publiciteApp.repository.TypePanneauRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;

@Slf4j
@Service
public class TypePanService {

    private final TypePanneauRepository typePanneauRepository;
    private final TypePanneauMapperClass typePanneauMapperClass;
    public TypePanService(TypePanneauRepository typePanneauRepository, TypePanneauMapperClass typePanneauMapperClass) {
        this.typePanneauRepository = typePanneauRepository;
        this.typePanneauMapperClass = typePanneauMapperClass;
    }

    public Long addType(TypePanRequest typePanRequest) throws IllegalAccessException {
        TypePanneau typePanneau1 = this.typePanneauRepository.findByLibelet(typePanRequest.libellet());

        if (typePanneau1 != null){
            throw new IllegalAccessException("Ce type existe déjà");
        }

        TypePanneau typePanneau = new TypePanneau();
        typePanneau.setLibelet(typePanRequest.libellet());
        return this.typePanneauRepository.save(typePanneau).getId();
    }

    public TypePanneau getType(Long id){
        return this.typePanneauRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Type panneau non trouvé"));
    }

    public List<TypePanResponse> getAllType() {
        //List<TypePanneau> typePanneaux = this.typePanneauRepository.findAll();
        List<TypePanResponse> typePanResponses=  this.typePanneauRepository.findAll().stream()
                .map(typePanneauMapperClass::ToTypePanneauResponse)
                .toList();
        log.info(" type response ***************" + typePanResponses.toString());
        return  typePanResponses;
    }

    public void deletePanneau(Long typeId) {
        TypePanneau typePanneau = this.typePanneauRepository.findById(typeId)
                .orElseThrow(()-> new EntityNotFoundException("Type panneau non trouvé"));
        this.typePanneauRepository.delete(typePanneau);
    }


    public TypePanResponse updateTypeById(Long id, @Valid TypePanRequest request) {
        TypePanneau typePanneau = this.typePanneauRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Type panneau non trouvé"));
        typePanneau.setLibelet(request.libellet());
        TypePanResponse  typePanResponse = new TypePanResponse();
        typePanResponse.setLibelet(typePanneau.getLibelet());
        return typePanResponse;
    }
}
