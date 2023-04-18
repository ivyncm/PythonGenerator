# -*- coding: utf-8 -*-
import qiskit, qiskit.circuit, qiskit_finance.circuit

from pricingEuropeanCallOptionsApp.businesslogic.entities import EuropeanCallProblem
from pricingEuropeanCallOptionsApp.businesslogic.entities import EuropeanCallObjective
from pricingEuropeanCallOptionsApp.classicalquantumlogic import EuropeanCallEstimation
from pricingEuropeanCallOptionsApp.classicalquantumlogic import EuropeanCallQuantumRequest
from pricingEuropeanCallOptionsApp.quantumlogic.algorithms import QuantumAmplitudeEstimationAlgorithm

class EuropeanCallDriver():
	
	def __init__(self):
		self.quantumRequest = EuropeanCallQuantumRequest
		self.QAEAlgorithm = QuantumAmplitudeEstimationAlgorithm
	
	def EuropeanCallDriver(self, problem: EuropeanCallProblem, objective: EuropeanCallObjective):
		pass
	
	def setQuantumRequestParameters(self, epsilon: float, alpha: float, shots: int):
		pass
	
	def estimateEuropeanCall(self) -> EuropeanCallEstimation:
		pass
	
	def getquantumRequest(self):
		return self.quantumRequest
	
	def setquantumRequest(self, value):
		self.quantumRequest = value
	
	def getQAEAlgorithm(self):
		return self.QAEAlgorithm
	
	def setQAEAlgorithm(self, value):
		self.QAEAlgorithm = value
	

