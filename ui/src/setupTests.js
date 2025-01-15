import MatchMediaMock from 'jest-matchmedia-mock';

new MatchMediaMock();

process.on('unhandledRejection', (reason) => {
  console.log('REJECTION', reason);
});
