package com.example.petstoregrpcjsonexample.grpc;

import com.example.petstoregrpcjsonexample.proto.AddPetRequest;
import com.example.petstoregrpcjsonexample.proto.GetPetByIdRequest;
import com.example.petstoregrpcjsonexample.proto.Pet;
import com.example.petstoregrpcjsonexample.proto.PetServiceGrpc;
import com.example.petstoregrpcjsonexample.service.PetDataService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@GrpcService
public class PetServiceImpl extends PetServiceGrpc.PetServiceImplBase {

    private final PetDataService petDataService;

    @Autowired
    public PetServiceImpl(PetDataService petDataService) {
        this.petDataService = petDataService;
    }

    @Override
    public void addPet(AddPetRequest request, StreamObserver<Pet> responseObserver) {
        com.example.petstoregrpcjsonexample.domain.Pet domainPet = petDataService.addPet(request.getName(), request.getStatus());

        Pet grpcPet = Pet.newBuilder()
                .setId(domainPet.getId())
                .setName(domainPet.getName())
                .setStatus(domainPet.getStatus())
                .build();

        responseObserver.onNext(grpcPet);
        responseObserver.onCompleted();
    }

    @Override
    public void getPetById(GetPetByIdRequest request, StreamObserver<Pet> responseObserver) {
        Optional<com.example.petstoregrpcjsonexample.domain.Pet> domainPetOptional = petDataService.getPetById(request.getPetId());

        if (domainPetOptional.isPresent()) {
            com.example.petstoregrpcjsonexample.domain.Pet domainPet = domainPetOptional.get();
            Pet grpcPet = Pet.newBuilder()
                    .setId(domainPet.getId())
                    .setName(domainPet.getName())
                    .setStatus(domainPet.getStatus())
                    .build();
            responseObserver.onNext(grpcPet);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("Pet not found with ID: " + request.getPetId())
                    .asRuntimeException());
        }
    }
}
