package com.mandarin.petching.repository;

import com.mandarin.petching.domain.Certificate;
import com.mandarin.petching.domain.QCertificate;
import com.mandarin.petching.domain.QPetSitter;
import com.mandarin.petching.domain.QReservation;
import com.mandarin.petching.dto.CertificateDTO;
import com.mandarin.petching.dto.SizeDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QDCertificateRepository {
    private final JPAQueryFactory queryFactory;
    private final QCertificate certificate = QCertificate.certificate1;

    public  List<CertificateDTO> findCountByCertificate()
    {
        List<CertificateDTO> result = queryFactory.select(Projections.bean(CertificateDTO.class, certificate.certificate.as("name"), certificate.certificate.count().as("count")))
                .from(certificate)
                .groupBy(certificate.certificate)
                .fetch();

        return result;
    }


}
