from django.conf.urls import url

from . import views

urlpatterns = [
    url(r'^food/$', views.food_list, name='index'),
    url(r'^food/(?P<pk>[0-9]+)/$', views.food_detail),
]
