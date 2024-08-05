import com.github.lucascalheiros.waterreminder.shared.di.appDataModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(appDataModule)
    }
}