package umc.spring.repository.storeRepository;

import umc.spring.domain.Store;

import java.util.List;

public interface StoreRepositoryCustom {

    List<Store> dynamicQueryWithBooleanBuilder(String name, Float score);

}
