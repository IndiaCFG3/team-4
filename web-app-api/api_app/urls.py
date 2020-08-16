from django.urls import path
from . import views

urlpatterns = [
    path('api/', views.GeneratePdf.as_view(), name='fetch_pdf')
]