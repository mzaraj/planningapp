package zarajczyk.marcin.planningapp.common;

public interface Mapper<ENTITY, VM> {
    ENTITY toEntity(VM VM);

    VM toVm(ENTITY entity);

}

