package ru.otus;

import ru.otus.appcontainer.AppComponentsContainerMult;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.configsplit.SplittedConfigOne;
import ru.otus.configsplit.SplittedConfigTwo;
import ru.otus.services.GameProcessor;

/*
В классе AppComponentsContainerImpl реализовать обработку, полученной в конструкторе конфигурации,
основываясь на разметке аннотациями из пакета appcontainer. Так же необходимо реализовать методы getAppComponent.
В итоге должно получиться работающее приложение. Менять можно только класс AppComponentsContainerImpl.
Можно добавлять свои исключения.

Раскоментируйте тест:
@Disabled //надо удалить
Тест и демо должны проходить для всех реализованных вариантов
Не называйте свой проект ДЗ "homework-template", это имя заготовки)

PS Приложение представляет собой тренажер таблицы умножения
*/

@SuppressWarnings({"squid:S125", "squid:S106"})
public class App {

    public static void main(String[] args) {
        // Опциональные варианты
        // AppComponentsContainer container = new AppComponentsContainerMult(SplittedConfigOne.class, SplittedConfigTwo.class);

        // Тут можно использовать библиотеку Reflections (см. зависимости)
         AppComponentsContainer container = new AppComponentsContainerMult("ru.otus.configsplit");

        // Обязательный вариант
//        AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig.class);

        // Приложение должно работать в каждом из указанных ниже вариантов
//                GameProcessor gameProcessor = container.getAppComponent(GameProcessor.class);
        //         GameProcessor gameProcessor = container.getAppComponent(GameProcessorImpl.class);
        GameProcessor gameProcessor = container.getAppComponent("gameProcessor");

        gameProcessor.startGame();
    }
}
