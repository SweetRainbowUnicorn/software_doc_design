from app import app
from app.constants import Config
# from app.utils import get_data_from_url
import redis
from app.loggers import context

r = redis.StrictRedis(host=Config.REDIS_HOST_NAME, port=6380,
                    password=Config.REDIS_KEY, ssl=True)

result = r.ping()
app.logger.info("Ping returned : " + str(result))
