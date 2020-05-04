package zarajczyk.marcin.planningapp.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import zarajczyk.marcin.planningapp.common.Mapper;
import zarajczyk.marcin.planningapp.domain.Tabl;
import zarajczyk.marcin.planningapp.vm.TablVm;

@Component
@Data
@AllArgsConstructor
public class TimeTableMapper implements Mapper<Tabl, TablVm> {

    private final ModelMapper modelMapper;

    @Override
    public Tabl toEntity(TablVm VM) {
        return modelMapper.map(VM, Tabl.class);
    }

    @Override
    public TablVm toVm(Tabl tabl) {
        return modelMapper.map(tabl, TablVm.class);
    }
}
