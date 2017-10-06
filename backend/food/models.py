from django.db import models

# Create your models here.
class Food(models.Model):
    humidity = models.FloatField()
    temperature = models.FloatField()
    stamp = models.DateTimeField(auto_now_add=True)
    class Meta:
        ordering = ('stamp',)
