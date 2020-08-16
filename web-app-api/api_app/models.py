from django.db import models
from phone_field import PhoneField


class Course(models.Model):
    course_name = models.CharField(max_length=30)
    faculty_name = models.CharField(max_length=30)
    ta_name = models.CharField(max_length=30)


class Student(models.Model):
    name = models.CharField(max_length=30)
    email = models.EmailField()
    contact_no = PhoneField(blank=True, help_text='Contact phone number')
    courses = models.ManyToManyField(Course)
    marks = models.PositiveIntegerField()
