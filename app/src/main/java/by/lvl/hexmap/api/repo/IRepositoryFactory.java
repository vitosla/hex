package by.lvl.hexmap.api.repo;

import by.lvl.hexmap.models.User.repo.IUserRepository;
import by.lvl.hexmap.models.UserLocation.repo.IUserLocationRepository;


public interface IRepositoryFactory {
    IUserRepository getUserRepository();
    IUserLocationRepository getUserLocationRepository();
}
