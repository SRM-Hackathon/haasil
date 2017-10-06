from rest_framework import serializers
from food.models import Food

class FoodSerializer(serializers.ModelSerializer):

    class Meta:
        model = Food
        fields = ('humidity', 'temperature')

    def create(self, validated_data):
        """
        Create and return a new `Food` instance, given the validated data.
        """
        return Food.objects.create(**validated_data)

    def update(self, instance, validated_data):
        """
        Update and return an existing `food` instance, given the validated data.
        """
        instance.humidity = validated_data.get('humidity', instance.humidity)
        instance.temperature = validated_data.get('temperature', instance.temperature)
        instance.save()
        return instance
