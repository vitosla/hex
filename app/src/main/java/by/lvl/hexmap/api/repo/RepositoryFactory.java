package by.lvl.hexmap.api.repo;

import by.lvl.hexmap.models.User.repo.IUserRepository;
import by.lvl.hexmap.models.User.repo.RemoteUserRepository;
import by.lvl.hexmap.models.UserLocation.repo.IUserLocationRepository;
import by.lvl.hexmap.models.UserLocation.repo.RemoteUserLocationRepository;


public class RepositoryFactory implements IRepositoryFactory {

    @Override
    public IUserRepository getUserRepository() {
        return new RemoteUserRepository();
    }

    @Override
    public IUserLocationRepository getUserLocationRepository() {
        return new RemoteUserLocationRepository();
    }
}
