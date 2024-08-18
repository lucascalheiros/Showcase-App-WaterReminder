import com.github.lucascalheiros.waterreminder.domain.history.domain.usecases.GetHistoryChartDataUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class HistoryInjector: KoinComponent {
    fun getHistoryChartDataUseCase(): GetHistoryChartDataUseCase = get()

}