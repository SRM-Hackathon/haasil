from django.db import models

# Create your models here.
class Food(models.Model):
    stamp = models.DateTimeField(auto_now_add=True)
    humidity = models.FloatField()
    temperature = models.FloatField()
    class Meta:
        ordering = ('stamp',)
