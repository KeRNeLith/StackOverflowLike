package fr.isima.stackoverflow.thirdpart

import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty
import grails.plugins.rest.client.RestBuilder
import grails.transaction.Transactional
import org.springframework.stereotype.Service
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand

@Transactional
@Service
class NumbersService
{
    /**
     * Random generator.
     */
    private Random random = new Random()

    /**
     * Get the meaning of a random number chosen randomly (From a third part API).
     * @return Random sentence.
     */
    @HystrixCommand(fallbackMethod = "getRandomNumberSentenceFallback")
    String getRandomNumberSentence()
    {
        String url = "http://numbersapi.com/"

        int random = getRandom(0, 200)

        def rest = new RestBuilder()
        def resp = rest.get(url + random)

        if (resp.status < 400)
            return resp.text
        else
            return getRandomNumberSentenceFallback()
    }

    /**
     * Get the meaning of a random number chosen randomly (Hardcoded and not from third part service).
     * @return Random sentence.
     */
    String getRandomNumberSentenceFallback()
    {
        return "666 is the number of the devil."
    }

    /**
     * Get random number between min and max
     * @param min Min random value.
     * @param max Max random value
     * @return Random value.
     */
    private int getRandom(int min, int max)
    {
        return random.nextInt(max + 1 - min) + min;
    }
}
