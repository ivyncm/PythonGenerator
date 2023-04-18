# -*- coding: utf-8 -*-
import qiskit, qiskit.circuit, qiskit_finance.circuit


class EuropeanCallObjective():
	
	def __init__(self):
		self.strikePrice = float()
		self.cApprox = float()
		self.breakpoints = float()
		self.slopes = float()
		self.offsets = float()
		self.linearAmplitudeFunction = qiskit.circuit.library.LinearAmplitudeFunction()
	
	def EuropeanCallObjective(self, strikePrice: float, cApprox: float, low: float, high: float, numUncertaintyQubits: int):
		pass
	
	def getstrikePrice(self):
		return self.strikePrice
	
	def setstrikePrice(self, value):
		self.strikePrice = value
	
	def getcApprox(self):
		return self.cApprox
	
	def setcApprox(self, value):
		self.cApprox = value
	
	def getbreakpoints(self):
		return self.breakpoints
	
	def setbreakpoints(self, value):
		self.breakpoints = value
	
	def getslopes(self):
		return self.slopes
	
	def setslopes(self, value):
		self.slopes = value
	
	def getoffsets(self):
		return self.offsets
	
	def setoffsets(self, value):
		self.offsets = value
	
	def getlinearAmplitudeFunction(self):
		return self.linearAmplitudeFunction
	
	def setlinearAmplitudeFunction(self, value):
		self.linearAmplitudeFunction = value
	

