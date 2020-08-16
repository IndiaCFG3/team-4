import datetime

from django.http import HttpResponse
from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView

from .models import Student
from .serializers import StudentSerializer
from .utils import render_to_pdf


class GenerateClassPdf(APIView):
    
    def get(self, request):
        students = Student.objects.all()
        serializer = StudentSerializer(students, many=True)
        pdf = render_to_pdf('pdf/class.html', {'students' : serializer.data})
        return HttpResponse(pdf, content_type='application/pdf')

    def post(self, request):
        serializer = StudentSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
 

class GenerateStudentPdf(APIView):
    
    def get_object(self, id):
        try:
            return Student.objects.get(id=id)
        except Student.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)
 
 
    def get(self, request, id):
        student = self.get_object(id)
        serializer = StudentSerializer(student)
        pdf = render_to_pdf('pdf/report.html', serializer.data)
        return HttpResponse(pdf, content_type='application/pdf')
 
 
 
    def put(self, request, id):
        student = self.get_object(id)
        serializer = StudentSerializer(student, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
 
    def delete(self, request, id):
        student = self.get_object(id)
        student.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)
