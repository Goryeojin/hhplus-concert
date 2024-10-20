package hhplus.concert.infra.repository.impl;

import hhplus.concert.domain.model.Point;
import hhplus.concert.domain.repository.PointRepository;
import hhplus.concert.infra.entity.PointEntity;
import hhplus.concert.infra.repository.jpa.PointJpaRepository;
import hhplus.concert.support.exception.CustomException;
import hhplus.concert.support.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepository {

    private final PointJpaRepository pointJpaRepository;

    @Override
    public Point findPoint(Long userId) {
        return pointJpaRepository.findByUserId(userId)
                .map(PointEntity::of)
                .orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR));
    }

    @Override
    public void save(Point updatedPoint) {
        PointEntity entity = PointEntity.from(updatedPoint);
        pointJpaRepository.save(entity);
    }
}
