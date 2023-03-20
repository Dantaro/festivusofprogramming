import React from 'react'

const LoggedInContext = React.createContext<boolean>(false)

export const LoggedInConsumer = LoggedInContext.Consumer
export const LoggedInProvider = LoggedInContext.Provider

export default LoggedInContext