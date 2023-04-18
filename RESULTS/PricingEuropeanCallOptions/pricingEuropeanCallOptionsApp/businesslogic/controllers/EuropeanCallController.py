# -*- coding: utf-8 -*-
import qiskit, qiskit.circuit, qiskit_finance.circuit

from pricingEuropeanCallOptionsApp.businesslogic.entities import EuropeanCallProblem
from pricingEuropeanCallOptionsApp.businesslogic.entities import EuropeanCallObjective

class EuropeanCallController():
	
	def __init__(self):
		self.problem = EuropeanCallProblem
		self.objective = EuropeanCallObjective
	
	def createProblem(self, spotPrice: float, volatility: float, anualRate: float, maturityDays: int):
		pass
	
	def createObjectiveFunction(self, strikePrice: float, cApprox: float):
		pass
	
	def getProblemPlot(self):
		pass
	
	def getproblem(self):
		return self.problem
	
	def setproblem(self, value):
		self.problem = value
	
	def getobjective(self):
		return self.objective
	
	def setobjective(self, value):
		self.objective = value
	

